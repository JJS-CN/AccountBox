import 'package:accountbox/const/gradient_const.dart';
import 'package:accountbox/const/color_const.dart';
import 'package:accountbox/const/gradient_const.dart';
import 'package:accountbox/const/images_const.dart';
import 'package:accountbox/ui/SignPageSix.dart';
import 'package:accountbox/widget/signup_6_box.dart';
import 'package:accountbox/widget/signup_const.dart';
import 'package:accountbox/widget/switch_button.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:get/get_rx/src/rx_types/rx_types.dart';

class BaseDialog extends StatefulWidget {
  Widget child;

  BaseDialog({
    Key? key,
    required this.child,
  }) : super(key: key);

  @override
  _BaseDialogState createState() {
    return _BaseDialogState();
  }
}

class _BaseDialogState extends State<BaseDialog> {
  @override
  Widget build(BuildContext context) {
    return GestureDetector(
        onTap: () => {Navigator.pop(context)},
        child: Scaffold(
          backgroundColor: Colors.transparent,
          body: GestureDetector(
            onTap: () {},
            child: Center(child: widget.child,),
          ),
        ));
  }
}
