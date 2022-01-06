import 'dart:convert';
import 'dart:math';

import 'package:accountbox/bean/entitys.dart';
import 'package:accountbox/const/app_const.dart';
import 'package:accountbox/const/color_const.dart';
import 'package:accountbox/const/images_const.dart';
import 'package:accountbox/dialog/create_account_dialog.dart';
import 'package:accountbox/dialog/salt_input_dialog.dart';
import 'package:accountbox/model/account_provider.dart';
import 'package:accountbox/routes/app_routes.dart';
import 'package:accountbox/widget/base_dialog.dart';
import 'package:crypto/crypto.dart';
import 'package:convert/convert.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/painting.dart';
import 'package:get/get.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';

class HomePage extends StatefulWidget {
  HomePage({Key? key}) : super(key: key);

  AccountProvider _accountProvider = AccountProvider();

  @override
  State<StatefulWidget> createState() {
    _accountProvider.onInit();
    return _HomePageState();
  }
}

class ItemStyleEntity {
  //是否展开
  bool expand = false;
  List<Color> colors = [];
}

class _HomePageState extends State<HomePage> {
  ScrollController _controller = new ScrollController();
  double itemGroupHeight = 50;
  double itemAccountHeight = 80;
  double itemAccountHeightExpand = 120;
  double _firstIndex = 0.0;
  int moveSize = 3;
  List<dynamic> list = [];

  //默认颜色值
  List<List<Color>> gradientColors = [];
  bool isListSettingOpen = false;

  double downDragDx = 0;
  double updateDragDx = 0;

  //原始数据
  List<GroupEntity> globaList = [];

  //临时控制参数，通过groupId进行比对
  Map<int, ItemStyleEntity> itemStyle = {};

  @override
  void initState() {
    // TODO: Account数据与Group数据颜色一样，只是样式不同
    //Account数据最多只展示3个，移出范围的进行缓慢折叠（因为已经不关注这部分数据了），，
    // 更进一步就是根据滚动快慢，滚动快折叠快，滚动慢甚至可以不折叠
    //滚动时Group进行平移动画，Account保持不变（为了进行数据视觉区分）
    //Account数据少于5条时，默认展开（为了填充页面）
    super.initState();

    gradientColors.add([Color(0xffffc2a1), Color(0xFFf194a2)]);
    gradientColors.add([Color(0xffe0c3fc), Color(0xff8ec5fc)]);
    gradientColors.add([Color(0xfffbed96), Color(0xffabecd6)]);
    gradientColors.add([Color(0xffc2e9fb), Color(0xFFa1c4fd)]);
    reloadGroups();
    updateList();

    moveSize = itemGroupHeight.toInt() ~/ 12;
    _controller.addListener(() {
      //_controller.offset回弹时有可能是负数，需要过滤
      if (_controller.offset > 0) {
        //向上滚动了，开始计算
        //第一个是哪个
        _firstIndex = _controller.offset / itemGroupHeight;
        setState(() {});
      }
    });
  }

  reloadGroups() {
    widget._accountProvider.getGroups(true).then((value) => {
          if (value != null) {globaList = value, updateList(), setState(() {})}
        });
  }

  updateList() {
    list.clear();

    GroupEntity? rootGroup;
    for (var i = 0; i < globaList.length; i++) {
      var group = globaList[i];
      var style =
          itemStyle.putIfAbsent(globaList[i].groupId, () => ItemStyleEntity());
      style.colors = gradientColors[i % gradientColors.length];
      itemStyle[globaList[i].groupId] = style;
      if (i == 0) {
        rootGroup = group;
        continue;
      }
      list.add(group);
      if (style.expand && group.accounts != null) {
        if (AppConst.salt?.isEmpty ?? true) {
          //未输入盐
        } else {
          var content = const Utf8Encoder().convert(AppConst.salt!);
          var digest = md5.convert(content);
          var en = hex.encode(digest.bytes);
          //unit为数字
          var unit = en[0].codeUnitAt(0);
          var salt64 = base64Encode(utf8.encode(AppConst.salt!)).toUpperCase();
          print("salt64：" + salt64 + "  unit:" + unit.toString());
          //需要移除的盐值
          var dSalt = salt64.replaceAll("=", "");

          for (var acc in group.accounts!) {
            if (acc.isEncryption &&
                (acc.decodePwd.isEmpty || acc.decodePwd == "******")) {
              //获取插入值
              try {
                print("解密：" + acc.password);
                var m =
                    unit % min<int>(10, acc.password.length - dSalt.length - 2);
                print("m：" + m.toString());
                var base64Pwd =
                    acc.password.replaceRange(m, m + dSalt.length, "");
                print("base64Pwd：" + base64Pwd);
                //对password进行加密
                var pwd = String.fromCharCodes(base64Decode(base64Pwd));
                print("pwd：" + pwd);
                acc.decodePwd = pwd;
              } catch (e) {
                e.printError();
                //解密失败
                acc.decodePwd = "******";
              }
            }
          }
        }

        list.addAll(group.accounts!);
      }
    }
    if (rootGroup != null && rootGroup.accounts != null) {
      list.addAll(rootGroup.accounts!);
    }
  }

// _onRefresh 下拉刷新回调
  Future<void> _onRefresh() {
    reloadGroups();
    return Future.delayed(Duration(seconds: 1), () {
      // 延迟2s完成刷新
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      resizeToAvoidBottomInset: false,
      body: Stack(
        children: [
          Container(
            width: double.infinity,
            height: double.infinity,
            decoration: BoxDecoration(
              image: DecorationImage(
                image: AssetImage(SignUpImagePath.SignUpPage_11_Bg),
                fit: BoxFit.cover,
                alignment: Alignment.bottomCenter,
              ),
            ),
          ),
          RefreshIndicator(
            onRefresh: _onRefresh,
            displacement: 80, //指示器显示时距顶部位置
            child: ListView.builder(
                controller: _controller,
                itemCount: list.length,
                physics: const AlwaysScrollableScrollPhysics(
                    parent: BouncingScrollPhysics()),
                itemBuilder: (BuildContext context, int index) {
                  double rightPadding = 0.0;
                  if (index <= _firstIndex) {
                    //全部
                    rightPadding = 0.80;
                  } else if (index - moveSize <= _firstIndex) {
                    //大于的，间隔3个数据，每个递减宽度0.1,但需要计算百分比
                    //计算得到高度
                    double l = (index - _firstIndex) / moveSize;
                    rightPadding = (0.70 + 0.1 - 0.1 * l);
                  } else {
                    rightPadding = 0.70;
                  }
                  //todo 动画可能需要提取小部件才行 https://www.nuomiphp.com/eplan/en/125068.html

                  if (list[index].runtimeType == GroupEntity) {
                    var group = list[index] as GroupEntity;

                    return Padding(
                      padding: EdgeInsets.fromLTRB(
                          0,
                          0,
                          context.width * (1 - rightPadding) -
                              (isListSettingOpen ? 30 : 0),
                          2),
                      child: InkWell(
                        onTap: () {
                          if ((group.accounts?.length ?? 0) > 0) {
                            var style = itemStyle[group.groupId];
                            style!.expand = !style.expand;
                            updateList();
                            setState(() {});
                          }
                        },
                        child: Container(
                          padding: EdgeInsets.only(left: 10),
                          height: itemGroupHeight,
                          width: double.maxFinite,
                          decoration: BoxDecoration(
                            gradient: LinearGradient(
                              begin: FractionalOffset.centerLeft,
                              end: FractionalOffset.centerRight,
                              // Add one stop for each color. Stops should increase from 0 to 1
                              stops: const [0.1, 0.6],
                              colors: itemStyle[group.groupId]!.colors,
                            ),
                            borderRadius: const BorderRadius.horizontal(
                              right: Radius.circular(15),
                            ),
                            boxShadow: const [
                              BoxShadow(
                                color: Colors.black26,
                                blurRadius: 20,
                                spreadRadius: 0,
                                offset: Offset(0, 10),
                              ),
                            ],
                          ),
                          child: Row(
                            children: [
                              Container(
                                padding: EdgeInsets.fromLTRB(0, 3, 5, 0),
                                child: FaIcon(
                                  (group.accounts?.length ?? 0) > 0
                                      ? itemStyle[group.groupId]!.expand
                                          ? FontAwesomeIcons.alignLeft
                                          : FontAwesomeIcons.alignJustify
                                      : FontAwesomeIcons.minus,
                                  size: 13,
                                ),
                              ),
                              Text(
                                group.groupName,
                                style: TextStyle(
                                    fontWeight: FontWeight.bold, fontSize: 16),
                              ),
                              Expanded(child: Container()),
                              if (isListSettingOpen)
                                Container(
                                  width: 30,
                                  padding: EdgeInsets.fromLTRB(5, 5, 5, 5),
                                  child: InkWell(
                                    onTap: () {
                                      widget._accountProvider
                                          .deleteGroup(group.groupId)
                                          .then((value) => {
                                                if (value != null)
                                                  {reloadGroups()}
                                              });
                                    },
                                    child: FaIcon(
                                      FontAwesomeIcons.trashAlt,
                                      color: Colors.white,
                                      size: 15,
                                    ),
                                  ),
                                )
                            ],
                          ),
                        ),
                      ),
                    );
                  } else {
                    //account
                    var account = list[index] as AccountEntity;
                    return Padding(
                      padding: EdgeInsets.fromLTRB(
                          0,
                          0,
                          context.width * (1 - rightPadding) +
                              20 -
                              (isListSettingOpen ? 30 : 0),
                          2),
                      child: Container(
                        padding: EdgeInsets.only(left: 10),
                        height: account.remarks.isEmpty
                            ? itemAccountHeight
                            : itemAccountHeightExpand,
                        width: context.width * 0.6,
                        decoration: BoxDecoration(
                          gradient: LinearGradient(
                            begin: FractionalOffset.centerLeft,
                            end: FractionalOffset.centerRight,
                            // Add one stop for each color. Stops should increase from 0 to 1
                            stops: const [0.1, 0.6],
                            colors: itemStyle[account.groupId]!.colors,
                          ),
                          borderRadius: const BorderRadius.horizontal(
                            right: Radius.circular(15),
                          ),
                          boxShadow: const [
                            BoxShadow(
                              color: Colors.black26,
                              blurRadius: 20,
                              spreadRadius: 0,
                              offset: Offset(0, 10),
                            ),
                          ],
                        ),
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Row(
                              children: [
                                Expanded(
                                    child: Container(
                                  padding: EdgeInsets.symmetric(vertical: 5),
                                  child: Text(
                                    account.accountName,
                                    style:
                                        TextStyle(fontWeight: FontWeight.bold),
                                  ),
                                )),
                                if (isListSettingOpen)
                                  Container(
                                    width: 30,
                                    padding: EdgeInsets.fromLTRB(5, 10, 10, 5),
                                    child: InkWell(
                                      onTap: () {
                                        widget._accountProvider
                                            .deleteAccount(account.accountId)
                                            .then((value) => {
                                                  if (value != null)
                                                    {reloadGroups()}
                                                });
                                      },
                                      child: FaIcon(
                                        FontAwesomeIcons.trashAlt,
                                        color: Colors.white,
                                        size: 13,
                                      ),
                                    ),
                                  )
                              ],
                            ),
                            Divider(
                              height: 1,
                              endIndent: 220,
                              color: Colors.black26,
                            ),
                            Expanded(flex: 2, child: Container()),
                            Text(account.passport),
                            Expanded(flex: 1, child: Container()),
                            Text(
                              account.isEncryption
                                  ? (AppConst.salt?.isEmpty ?? true) ||
                                          account.decodePwd.isEmpty
                                      ? "******"
                                      : account.decodePwd
                                  : account.password,
                              style: TextStyle(
                                  color: account.decodePwd.isNotEmpty &&
                                          (AppConst.salt?.isNotEmpty ?? false)
                                      ? account.decodePwd == "******"
                                          ? Colors.red
                                          : BLUE_DEEP
                                      : Colors.black),
                            ),
                            Expanded(flex: 2, child: Container()),
                            if (!account.remarks.isEmpty)
                              Container(
                                height: 40,
                                child: Column(
                                  crossAxisAlignment: CrossAxisAlignment.start,
                                  children: [
                                    Divider(
                                      height: 1,
                                      endIndent: 150,
                                      color: Colors.black26,
                                    ),
                                    Expanded(child: Container()),
                                    Container(
                                      child: Text(
                                        account.remarks,
                                        style: TextStyle(
                                            fontSize: 11,
                                            color: Colors.black38),
                                        maxLines: 2,
                                        overflow: TextOverflow.ellipsis,
                                      ),
                                    ),
                                    Expanded(child: Container()),
                                  ],
                                ),
                              )
                          ],
                        ),
                      ),
                    );
                  }
                }),
          ),
          Positioned(
              right: 20,
              bottom: 100,
              child: GestureDetector(
                onTap: () {
                  isListSettingOpen = !isListSettingOpen;
                  setState(() {});
                },
                onHorizontalDragDown: (DragDownDetails value) {
                  downDragDx = value.localPosition.dx;
                  updateDragDx = 0;
                },
                onHorizontalDragEnd: (DragEndDetails value) {
                  var dx = updateDragDx - downDragDx;
                  if (dx < 1) {
                    //左滑，展开
                    isListSettingOpen = true;
                  } else {
                    //右滑,隐藏
                    isListSettingOpen = false;
                  }
                  setState(() {});
                },
                onHorizontalDragUpdate: (DragUpdateDetails value) {
                  updateDragDx = value.localPosition.dx;
                },
                child: Container(
                  width: isListSettingOpen ? 40 : 40,
                  padding: EdgeInsets.fromLTRB(5, 15, 8, 10),
                  margin: EdgeInsets.only(left: 1000),
                  child: FaIcon(
                    FontAwesomeIcons.cogs,
                    color: Colors.white,
                    size: 16,
                  ),
                ),
              )),
          Positioned(
              right: 20,
              bottom: 150,
              child: InkWell(
                  onTap: () {
                    if (AppConst.salt?.isEmpty ?? true) {
                      //当前是空的，弹出加盐
                      DialogManager.showSaltInputDialog(
                          (isAddSuccess) => {setState(() {})});
                    } else {
                      //当前是有的，进行清空
                      AppConst.salt = null;
                    }
                    updateList();
                    setState(() {});
                  },
                  child: Container(
                    width: 40,
                    padding: EdgeInsets.fromLTRB(5, 10, 8, 10),
                    child: Center(
                      child: FaIcon(
                        AppConst.salt?.isEmpty ?? true
                            ? FontAwesomeIcons.lock
                            : FontAwesomeIcons.unlock,
                        color: AppConst.salt?.isEmpty ?? true ? RED : GREEN,
                        size: 16,
                      ),
                    ),
                  ))),
          Positioned(
              right: 10,
              top: 50,
              child: IconButton(
                icon: FaIcon(
                  FontAwesomeIcons.ellipsisH,
                  color: Colors.white,
                  size: 20,
                ),
                onPressed: () {
                  Get.toNamed(Routes.AppSetting);
                },
              ))
        ],
      ),
      floatingActionButtonLocation: FloatingActionButtonLocation.endFloat,
      floatingActionButton: FloatingActionButton(
          backgroundColor: BLUE_DEEP,
          child: FaIcon(
            FontAwesomeIcons.plus,
            size: 15,
          ),
          onPressed: () => {
                Get.dialog(BaseDialog(
                  child: createAccountDialog(
                      context,
                      (isAdd) => {
                            if (isAdd) {reloadGroups()}
                          }),
                ))
                // Get.defaultDialog()
              }),
    );
  }
}
