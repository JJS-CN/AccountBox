import 'package:accountbox/const/color_const.dart';
import 'package:accountbox/const/icons.dart';
import 'package:accountbox/const/images_const.dart';
import 'package:accountbox/controller/setting_controller.dart';
import 'package:accountbox/widget/signup_arrow_button.dart';
import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:get/get.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';

class AccountInfoPage extends GetView<SettingController> {
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
                    itemBuilder: (BuildContext context, int index) {
                      return Stack(
                        children: [
                          Center(child: Container(
                            width: 300,
                            height: 100,
                            decoration: BoxDecoration(
                              gradient: LinearGradient(
                                begin: FractionalOffset.topLeft,
                                end: FractionalOffset.bottomRight,
                                // Add one stop for each color. Stops should increase from 0 to 1
                                stops: const [0.2, 0.8],
                                colors: [Color(0xfffbed96), Color(0xffabecd6)],
                              ),
                              borderRadius: const BorderRadius.horizontal(
                                right: Radius.circular(10),
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
                          ),)
                        ],
                      );
                    }),
              ),
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
