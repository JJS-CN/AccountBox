import 'package:flutter/material.dart';
import 'package:accountbox/const/color_const.dart';
import 'package:accountbox/const/icons.dart';
import 'package:accountbox/widget/signup_arrow_button.dart';
import 'package:get/get.dart';

class SignupApbar extends StatelessWidget implements PreferredSizeWidget {
  final String title;

  SignupApbar({required this.title});

  @override
  Widget build(BuildContext context) {
    final double statusbarHeight = MediaQuery.of(context).padding.top;

    return Container(
      padding: EdgeInsets.only(top: statusbarHeight),
      decoration: BoxDecoration(
          gradient: LinearGradient(
        begin: FractionalOffset(0.5, 0.0), end: FractionalOffset(0.6, 0.8),
        // Add one stop for each color. Stops should increase from 0 to 1
        stops: [0.0, 0.9], colors: [YELLOW, BLUE],
      )),
      child: NavigationToolbar(
        leading: Align(
          alignment: Alignment(-0.5, 4),
          child: SignUpArrowButton(
            onTap: () => Get.back(),
            icon: const IconData(
              arrow_left,
              fontFamily: "Icons",
            ),
            iconSize: 9,
            height: 48,
            width: 48,
          ),
        ),
        centerMiddle: true,
        middle: Text(
          title,
          style: TextStyle(fontWeight: FontWeight.bold),
        ),
      ),
    );
  }

  @override
  Size get preferredSize => Size.fromHeight(kToolbarHeight);
}
