import 'package:accountbox/const/color_const.dart';
import 'package:accountbox/const/icons.dart';
import 'package:accountbox/const/images_const.dart';
import 'package:accountbox/controller/setting_controller.dart';
import 'package:accountbox/routes/app_routes.dart';
import 'package:accountbox/widget/signup_arrow_button.dart';
import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:get/get.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';

class SettingPage extends GetView<SettingController> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
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
          Column(
            children: [
              Expanded(
                child: ListView.builder(
                    shrinkWrap: true,
                    itemCount: 10,
                    itemExtent: 50,
                    itemBuilder: (BuildContext context, int index) {
                      return Row(children: [
                        FaIcon(
                          FontAwesomeIcons.child,
                          size: 16,
                          color: Colors.grey,
                        ),
                        Text("Setting" + index.toString()),
                      ]);
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
                      boxShadow: [
                        BoxShadow(
                            color: Colors.blueGrey,
                            spreadRadius: 1,
                            offset: Offset(2, 2),
                            blurRadius: 5)
                      ]),
                  child: Text(
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
