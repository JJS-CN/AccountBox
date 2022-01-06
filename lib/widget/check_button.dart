import 'package:flutter/material.dart';

Widget CheckButtonBlack(String text,
    {GestureTapCallback? onTap,EdgeInsetsGeometry? padding}) {
  return InkWell(
    onTap: () {
      onTap!.call();
    },
    child: Container(
      padding:
          padding ?? EdgeInsets.symmetric(horizontal: 20.0, vertical: 10.0),
      decoration: BoxDecoration(
          boxShadow: [
            BoxShadow(
                color: Colors.black12,
                blurRadius: 15,
                spreadRadius: 0,
                offset: Offset(0.0, 12.0)),
          ],
          borderRadius: new BorderRadius.circular(36.0),
          gradient: LinearGradient(begin: FractionalOffset.centerLeft,
// Add one stop for each color. Stops should increase from 0 to 1
              stops: [
                0.2,
                1
              ], colors: [
            Color(0xff000000),
            Color(0xff434343),
          ])),
      child: Text(
        text,
        style: TextStyle(
            color: Color(0xffF1EA94),
            fontWeight: FontWeight.bold,
            fontFamily: 'Montserrat'),
      ),
    ),
  );
}
