import 'dart:convert';
import 'dart:math';

import 'package:accountbox/bean/entitys.dart';
import 'package:accountbox/const/app_const.dart';
import 'package:accountbox/const/gradient_const.dart';
import 'package:accountbox/const/color_const.dart';
import 'package:accountbox/dialog/salt_input_dialog.dart';
import 'package:accountbox/model/account_provider.dart';
import 'package:accountbox/widget/check_button.dart';
import 'package:accountbox/widget/switch_button.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/painting.dart';
import 'package:get/get.dart' hide Value;
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:get/get_rx/src/rx_types/rx_types.dart';
import 'package:crypto/crypto.dart';
import 'package:convert/convert.dart';

Widget createAccountDialog(
    BuildContext context, Function(bool isAddSuccess) call) {
  var currentIndex = 0.obs;
  var tabs = ["Account", "Group"];
  var bodyHeight = context.height * 0.51;
  var _AccountPage = CreateAccountPage(
    callBack: call,
  );
  var _GroupPage = CreateGroupPage(
    callBack: call,
  );
//todo 这里的Container需要更换为StateFul估计才能动态高度！！！
  return UnconstrainedBox(
    child: Stack(
      children: [
        Container(
          constraints:
              BoxConstraints(minHeight: bodyHeight, maxHeight: double.infinity),
          width: context.width * 0.8,
          decoration: BoxDecoration(
            gradient: SIGNUP_CARD_BACKGROUND,
            borderRadius: const BorderRadius.all(
              Radius.circular(10),
            ),
            boxShadow: const [
              BoxShadow(
                color: Colors.black26,
                blurRadius: 20,
                spreadRadius: 0,
                offset: Offset(0, 5),
              ),
            ],
          ),
        ),
        Positioned(
          top: 10,
          left: 0,
          right: 0,
          child: Column(
            mainAxisSize: MainAxisSize.min,
            crossAxisAlignment: CrossAxisAlignment.center,
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              SwitchButton(
                width: 160,
                height: 35,
                selectColor: BLUE,
                tabs: tabs,
                currentIndex: 0,
                switchCallback: (index) => {
                  currentIndex.value = index,
                },
              ),
              Obx(() {
                return Container(
                  constraints: BoxConstraints(
                      minHeight: bodyHeight, maxHeight: double.infinity),
                  child: currentIndex == 0 ? _AccountPage : _GroupPage,
                );
              }),
            ],
          ),
        ),
        Positioned(
            top: 0,
            right: 0,
            child: GestureDetector(
              child: Padding(
                padding: EdgeInsets.symmetric(vertical: 6, horizontal: 10),
                child: FaIcon(
                  FontAwesomeIcons.times,
                  color: Colors.white,
                ),
              ),
              onTap: () {
                Get.back();
              },
            )),
      ],
    ),
  );
}

class CreateAccountPage extends StatefulWidget {
  TextEditingController _username = TextEditingController();
  TextEditingController _alias = TextEditingController();

  TextEditingController _password = TextEditingController();
  TextEditingController _desc = TextEditingController();
  FocusNode _usernameFocusNode = FocusNode();
  FocusNode _aliasFocusNode = FocusNode();
  FocusNode _passwordFocusNode = FocusNode();
  final GlobalKey<FormState> _formKey = GlobalKey<FormState>();

  final AccountProvider _accountProvider = AccountProvider();

  //是否需要加密，如果临时数据有加盐参数，就默认开启，否则为false
  var needEncryption = false;

  //弹窗选择列表数据
  List<PopupMenuItem<GroupEntity>> groupPopList = [];

  //
  GroupEntity? selectGroup;
  void Function(bool isAddSuccess) callBack;

  CreateAccountPage({Key? key, required this.callBack}) : super(key: key);

  @override
  _CreateAccountPageState createState() {
    _accountProvider.onInit();
    return _CreateAccountPageState();
  }
}

class _CreateAccountPageState extends State<CreateAccountPage> {
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    widget._username.addListener(() {
      setState(() {});
    });
    widget._alias.addListener(() {
      setState(() {});
    });
    widget._password.addListener(() {
      setState(() {});
    });
    widget._usernameFocusNode.addListener(() {
      setState(() {});
    });
    widget._aliasFocusNode.addListener(() {
      setState(() {});
    });
    widget._passwordFocusNode.addListener(() {
      setState(() {});
    });
    widget._accountProvider.getGroups(false).then((value) => {
          if (value != null)
            {
              widget.groupPopList.clear(),
              for (var gr in value)
                {
                  if (widget.selectGroup == null) {widget.selectGroup = gr},
                  widget.groupPopList.add(PopupMenuItem(
                    child: Text(gr.groupName),
                    padding: EdgeInsets.only(left: 10, top: 5, bottom: 5),
                    height: 0,
                    value: gr,
                  ))
                }
            },
          setState(() {})
        });
    if (AppConst.salt?.isNotEmpty ?? false) {
      //如果有加盐，默认开启加密开关
      widget.needEncryption = true;
    }
    //value填充groupId
  }

  @override
  void dispose() {
    super.dispose();
    //类似于Android的onDestroy
    widget._usernameFocusNode.unfocus();
    widget._aliasFocusNode.unfocus();
    widget._passwordFocusNode.unfocus();
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        Padding(
          padding: EdgeInsets.fromLTRB(20, 20, 20, 20),
          child: Form(
            key: widget._formKey,
            child: Column(
              mainAxisSize: MainAxisSize.min,
              children: <Widget>[
                buildInputText("ALIAS", 'xxxx', widget._alias,
                    widget._aliasFocusNode, TextInputType.text, (value) {
                  if (value!.length <= 0) {
                    return '别名不能为空';
                  }
                }, false),
                buildInputText(
                    "ACCOUNT",
                    'xxxx@gmail.com',
                    widget._username,
                    widget._usernameFocusNode,
                    TextInputType.emailAddress, (value) {
                  if (value!.length <= 0) {
                    return '账号不能为空';
                  }
                }, false),
                buildInputText(
                    "PASSWORD",
                    '******',
                    widget._password,
                    widget._passwordFocusNode,
                    TextInputType.visiblePassword, (value) {
                  if (value!.length < 0) {
                    return '密码不能为空';
                  }
                }, true),
                TextField(
                  style: TextStyle(fontSize: 12),
                  controller: widget._desc,
                  maxLength: 100,
                  minLines: 1,
                  maxLines: 3,
                  decoration: InputDecoration(
                    label: Text("Desc"),
                    hintText: "xxxx",
                    labelStyle: TextStyle(
                      fontSize: 12,
                      fontFamily: "Montserrat",
                      letterSpacing: 0.4,
                      height: 0.5,
                    ),
                    hintStyle: TextStyle(color: Colors.grey),
                    helperStyle: TextStyle(fontSize: 8),
                    focusedBorder: UnderlineInputBorder(
                        borderSide:
                            BorderSide(color: Colors.lightBlue, width: 1)),
                  ),
                ),
                Row(
                  children: [
                    Text("加密"),
                    Switch(
                        value: widget.needEncryption,
                        autofocus: true,
                        onChanged: (value) {
                          if (AppConst.salt?.isEmpty ?? true) {
                            DialogManager.showSaltInputDialog((value) => {
                                  widget.callBack(value),
                                  widget.needEncryption = true,
                                  setState(() {})
                                });
                            return;
                          }
                          widget.needEncryption = value;
                          setState(() {});
                        }),
                    Expanded(child: Container()),
                    Text("Group"),
                    PopupMenuButton(
                        offset: Offset(0, 20),
                        padding: EdgeInsets.all(1),
                        shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(10)),
                        child: Container(
                          constraints: BoxConstraints(minWidth: 50),
                          margin: EdgeInsets.only(left: 5),
                          padding:
                              EdgeInsets.symmetric(horizontal: 8, vertical: 2),
                          decoration: BoxDecoration(
                            color: BLUE,
                            borderRadius: BorderRadius.circular(30),
                          ),
                          child: Center(
                            child: Text(widget.selectGroup?.groupName ?? "/"),
                          ),
                        ),
                        onSelected: (value) {
                          widget.selectGroup = value as GroupEntity;
                          setState(() {});
                        },
                        itemBuilder: (BuildContext context) {
                          //生成item弹出时，关闭输入法
                          FocusScope.of(context).requestFocus(FocusNode());
                          return widget.groupPopList.length <= 1
                              ? []
                              : widget.groupPopList;
                        })
                  ],
                )
              ],
            ),
          ),
        ),
        CheckButtonBlack("Creat Account", onTap: () {
          if (widget._formKey.currentState?.validate() ?? false) {
            var encodePwd;
            if (widget.needEncryption) {
              //需要进行加密
              var content = const Utf8Encoder().convert(AppConst.salt!);
              var digest = md5.convert(content);
              var en = hex.encode(digest.bytes);
              //unit为数字
              var unit = en[0].codeUnitAt(0);

              //对password进行加密
              var base64 = base64Encode(utf8.encode(widget._password.text));

              //在10以内取模
              var m = unit % min<int>(10, base64.length - 2);
              print("原始base64:" + base64);
              //截取第一段
              var s1 = base64.substring(0, m);
              print(s1);
              //截取第二段
              var s2 = base64.substring(m, base64.length);
              print(s2);
              var salt64 =
                  base64Encode(utf8.encode(AppConst.salt!)).toUpperCase();
              print("salt64:" + salt64);
              encodePwd = s1 + salt64.replaceAll("=", "") + s2;
              print(encodePwd);
            } else {
              encodePwd = widget._password.text;
            }
            widget._accountProvider
                .addAccount(
                    widget.selectGroup?.groupId ?? 0,
                    widget._alias.text,
                    widget._username.text,
                    encodePwd,
                    widget._desc.text,
                    widget.needEncryption)
                .then((value) => {
                      if (value != null) {Get.back(), widget.callBack(true)}
                    });
          }
        })
      ],
    );
  }
}

class CreateGroupPage extends StatefulWidget {
  TextEditingController _alias = TextEditingController();
  FocusNode _aliasFocusNode = FocusNode();
  final GlobalKey<FormState> _formKey = GlobalKey<FormState>();
  final AccountProvider _accountProvider = AccountProvider();

  //是否需要加密，如果临时数据有加盐参数，就默认开启，否则为false
  var needEncryption = false;

  //
  int selectGroupId = 0;
  void Function(bool isAddSuccess) callBack;

  CreateGroupPage({Key? key, required this.callBack}) : super(key: key);

  @override
  _CreateGroupPageState createState() {
    _accountProvider.onInit();
    return _CreateGroupPageState();
  }
}

class _CreateGroupPageState extends State<CreateGroupPage> {
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    widget._alias.addListener(() {
      setState(() {});
    });
    widget._aliasFocusNode.addListener(() {
      setState(() {});
    });
  }

  @override
  void dispose() {
    super.dispose();
    //类似于Android的onDestroy
    widget._aliasFocusNode.unfocus();
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        Padding(
          padding: EdgeInsets.fromLTRB(20, 20, 20, 20),
          child: Form(
            key: widget._formKey,
            child: Column(
              mainAxisSize: MainAxisSize.min,
              children: <Widget>[
                buildInputText("ALIAS", 'xxxx', widget._alias,
                    widget._aliasFocusNode, TextInputType.text, (value) {
                  if (value!.length <= 0) {
                    return '别名不能为空';
                  }else if(value=="/"){
                    return "不能为关键字'/'";
                  }
                }, false),
              ],
            ),
          ),
        ),
        CheckButtonBlack("Creat Group", onTap: () {
          if (widget._formKey.currentState?.validate() ?? false) {
            widget._accountProvider
                .addGroup(widget._alias.text)
                .then((value) => {
                      if (value != null) {Get.back(), widget.callBack(true)}
                    });
          }
        })
      ],
    );
  }
}

Widget buildInputText(
  String fieldName,
  String hintText,
  TextEditingController controller,
  FocusNode? focusNode,
  TextInputType? textInputType,
  FormFieldValidator<String>? validator,
  bool obSecure,
) {
  IconData deleteIcon = FontAwesomeIcons.times;
  return TextFormField(
    focusNode: focusNode,
    style: TextStyle(fontSize: 13),
    controller: controller,
    validator: validator,
    keyboardType: textInputType,
    decoration: InputDecoration(
      hintText: hintText,
      hintStyle: TextStyle(color: Colors.grey),
      labelText: fieldName,
      labelStyle: TextStyle(
        fontSize: 12,
        fontFamily: "Montserrat",
        letterSpacing: 0.4,
        height: 0.5,
      ),
      suffixIcon: controller.text.length > 0 && (focusNode?.hasFocus ?? false)
          ? IconButton(
              //去除点击动画效果
              splashColor: Colors.transparent,
              highlightColor: Colors.transparent,
              icon: FaIcon(
                deleteIcon,
                size: 15,
              ),
              onPressed: () => {controller.clear()},
            )
          : null,
      focusedBorder: UnderlineInputBorder(
          borderSide: BorderSide(color: Colors.lightBlue, width: 1)),
    ),
    obscureText: obSecure,
  );
}
