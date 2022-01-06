import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

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
