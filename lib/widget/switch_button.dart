import 'package:flutter/material.dart';

typedef SwitchCallback = void Function(int index);

// ignore: must_be_immutable
class SwitchButton extends StatefulWidget {
  double? width = 120;
  double? height = 40;
  List<String> tabs;
  PageController? controller;
  Color? unSelectColor;
  Color? selectColor;
  int currentIndex;
  SwitchCallback? switchCallback;

  SwitchButton({
    Key? key,
    required this.width,
    required this.height,
    this.controller,
    required this.tabs,
    this.unSelectColor,
    this.selectColor,
    required this.currentIndex,
    this.switchCallback,
  }) : super(key: key);

  @override
  _SwitchButtonState createState() {
    return _SwitchButtonState();
  }
}

class _SwitchButtonState extends State<SwitchButton> {
  late double centerPoint;
  Duration _duration = Duration(milliseconds: 200);
  double _padding = 3;
  double _dragDistance = 0;

  @override
  void initState() {
    super.initState();
    this.centerPoint = widget.width! / 2;
  }

  @override
  void dispose() {
    super.dispose();
  }

  changePage(int currentIndex) {
    widget.currentIndex = currentIndex;
    if (widget.controller != null) {
      widget.controller!.animateToPage(
        currentIndex,
        duration: _duration,
        curve: Curves.ease,
      );
    }
    widget.switchCallback!.call(currentIndex);
    setState(() {});
  }

  backgroundWidget() {
    return GestureDetector(
      child: Container(
        width: widget.width,
        height: widget.height,
        decoration: BoxDecoration(
          border: Border.all(),
          borderRadius: BorderRadius.circular(widget.height! / 2),
        ),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: <Widget>[
            Text(
              widget.tabs[0],
              style: TextStyle(
                color: Colors.black,
                fontSize: 14,
                fontWeight: FontWeight.w600,
              ),
            ),
            Text(
              widget.tabs[1],
              style: TextStyle(
                color: Colors.black,
                fontSize: 14,
                fontWeight: FontWeight.w600,
              ),
            ),
          ],
        ),
      ),
      onTap: () {
        int currentIndex;
        if (widget.currentIndex == 0) {
          currentIndex = 1;
        } else {
          currentIndex = 0;
        }
        changePage(currentIndex);
      },
    );
  }

  switchWidget() {
    return AnimatedPositioned(
      left: widget.currentIndex == 0 ? 0 : this.centerPoint - _padding,
      duration: _duration,
      child: GestureDetector(
        onHorizontalDragUpdate: (DragUpdateDetails value) {
          _dragDistance = value.localPosition.dx;
        },
        onHorizontalDragEnd: (DragEndDetails value) {
          if (_dragDistance < -10) {
            changePage(0);
          } else if (_dragDistance > 10) {
            changePage(1);
          }
        },
        onHorizontalDragStart: (value) {
          _dragDistance = 0;
        },
        child: Container(
          width: centerPoint - _padding,
          height: widget.height! - _padding * 2,
          decoration: BoxDecoration(
            color: widget.selectColor ?? Colors.white,
            borderRadius: BorderRadius.circular(
              (widget.height! - _padding * 2) / 2,
            ),
          ),
          margin: EdgeInsets.all(_padding),
          child: Center(
            child: Text(
              widget.tabs[widget.currentIndex],
              style: TextStyle(
                color: Colors.black,
                fontSize: 14,
                fontWeight: FontWeight.w600,
              ),
            ),
          ),
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: <Widget>[
        backgroundWidget(),
        switchWidget(),
      ],
    );
  }
}
