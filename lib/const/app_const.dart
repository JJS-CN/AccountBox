import 'package:accountbox/bean/entitys.dart';

class AppConst {
  //用户数据
  static UserEntity user = UserEntity(0, "", "");

  //加盐临时存储
  static String? salt;

  //退出时需要进行数据清空
  static exit() {
    user = UserEntity(0, "", "");
    salt = null;
  }
}
