import 'package:accountbox/const/images_const.dart';
import 'package:accountbox/controller/setting_controller.dart';
import 'package:accountbox/routes/app_routes.dart';
import 'package:accountbox/widget/signup_arrow_button.dart';
import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:get/get.dart';
import 'dart:math';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';

class SettingPage extends StatefulWidget {
  @override
  _SettingPageState createState() {
    return _SettingPageState();
  }
}

class _SettingPageState extends State<SettingPage> {
  ScrollController _controller = new ScrollController();
  double _firstIndex = 0.5;
  int moveSize = 3;
  double itemGroupHeight = 50;
  List<SettingEntity> list = [];

  @override
  void initState() {
    super.initState();
    list.clear();
    list.add(SettingEntity("使用说明"));
    list.add(SettingEntity("加密说明"));
    list.add(SettingEntity("xxx"));
    list.add(SettingEntity("xxx"));
    list.add(SettingEntity("xxx"));
    list.add(SettingEntity("xxx"));
    list.add(SettingEntity("xxx"));
    _controller.addListener(() {
      //_controller.offset回弹时有可能是负数，需要过滤
      if (_controller.offset > 0) {
        //向上滚动了，开始计算
        //第一个是哪个
        _firstIndex = _controller.offset / itemGroupHeight+0.5;
        setState(() {});
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        children: [
          Container(
            width: double.infinity,
            height: double.infinity,
            decoration: const BoxDecoration(
              image: DecorationImage(
                image: AssetImage(SignUpImagePath.SignUpPage_11_Bg),
                fit: BoxFit.cover,
                alignment: Alignment.bottomCenter,
              ),
            ),
          ),
          Column(
            children: [
              Expanded(
                child: ListView.builder(
                    shrinkWrap: true,
                    controller: _controller,
                    itemCount: list.length,
                    itemExtent: itemGroupHeight,
                    physics: const AlwaysScrollableScrollPhysics(
                        parent: BouncingScrollPhysics()),
                    itemBuilder: (BuildContext context, int index) {
                      double rightPadding = 0.0;
                      if (index <= _firstIndex) {
                        //全部
                        rightPadding = 0.78;
                      } else if (index - moveSize <= _firstIndex) {
                        //大于的，间隔3个数据，每个递减宽度0.1,但需要计算百分比
                        //计算得到高度
                        double l = (index - _firstIndex) / moveSize;
                        rightPadding = (0.98 - 0.2 + 0.2 * l);
                      } else {
                        rightPadding = 0.98;
                      }
                      SettingEntity entity = list[index];
                      return Padding(
                        padding: EdgeInsets.fromLTRB(
                            context.width * (1 - rightPadding), 0, 10, 2),
                        child: Container(
                          padding: const EdgeInsets.fromLTRB(10, 0, 10, 0),
                          decoration: const BoxDecoration(
                            color: Colors.white,
                            borderRadius: BorderRadius.all(
                              Radius.circular(8),
                            ),
                            boxShadow: [
                              BoxShadow(
                                color: Colors.black26,
                                blurRadius: 20,
                                spreadRadius: 0,
                                offset: Offset(0, 10),
                              ),
                            ],
                          ),
                          child: InkWell(
                            onTap: () {
                              if (entity.pageName != null) {
                                Get.toNamed(entity.pageName!);
                              }
                            },
                            child: Row(children: [
                              FaIcon(
                                entity.icon,
                                size: 16,
                                color: Colors.grey,
                              ),
                              Text(entity.title),
                            ]),
                          ),
                        ),
                      );
                    }),
              ),
              InkWell(
                onTap: () {
                  Get.offAllNamed(Routes.UserLogin);
                },
                child: Container(
                  margin: EdgeInsets.only(bottom: 60),
                  padding: EdgeInsets.symmetric(horizontal: 40, vertical: 8),
                  decoration: BoxDecoration(
                      border: Border.all(color: Colors.white70),
                      color: Colors.white,
                      borderRadius: BorderRadius.circular(20),
                      boxShadow: const [
                        BoxShadow(
                            color: Colors.blueGrey,
                            spreadRadius: 1,
                            offset: Offset(2, 2),
                            blurRadius: 5)
                      ]),
                  child: const Text(
                    "退出登录",
                    style: TextStyle(
                        color: Colors.red,
                        fontSize: 16,
                        fontWeight: FontWeight.w600),
                  ),
                ),
              )
            ],
          ),
          Positioned(
            top: 60,
            left: 15,
            child: SignUpArrowButton(
              icon: FontAwesomeIcons.arrowLeft,
              iconSize: 15,
              onTap: () => {
                Get.back(),
              },
            ),
          )
        ],
      ),
    );
  }
}

class SettingEntity {
  IconData? icon = FontAwesomeIcons.cog;
  String title = "";
  String? pageName;

  SettingEntity(this.title, {this.icon = FontAwesomeIcons.cog, this.pageName});
}
