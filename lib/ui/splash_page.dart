import 'package:accountbox/controller/splash_controller.dart';
import 'package:accountbox/ui/home_page.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:dio/dio.dart';
import 'login_page.dart';

class SplashPage extends StatelessWidget {
  SplashPage({Key? key}) : super(key: key);
  var splashController = Get.put(SplashController());

  @override
  Widget build(BuildContext context) {
    //   splashController.loadLoginState();

    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: Text("SplashPage".tr),
        ),
        body: Center(
          child: MaterialButton(
            child: Text("hello".tr),
            onPressed: () {
              splashController.loadLoginState();
            },
          ),
        ),
      ),
    );
  }
}
