import 'package:accountbox/ui/home_page.dart';
import 'package:accountbox/ui/login_page.dart';
import 'package:get/get.dart';
import 'package:dio/dio.dart';

//创建一个业务逻辑类，并将所有的变量，方法和控制器放在里面
class SplashController extends GetxController {
  //通过obs方法，将变量设置为可观察的，类似LiveData
  var isLogin = false.obs;

  loadLoginState() {
    print("loadLoginState");
    Future.delayed(Duration(milliseconds: 500), () {
      if (isLogin.value) {
        isLogin.value = false;
        Get.to(HomePage());
      } else {
        isLogin.value = true;
        Get.to(LoginPage());
      }
    });

    /* var response = await Dio().get(
        "https://apis.juhe.cn/fapig/douyin/billboard?type=hot_video&size=50&key=9eb8ac7020d9bea6048db1f4c6b6d028");
    if (response.statusCode == 200) {
      Get.to(const HomePage());
    } else {
      Get.to(const LoginPage());
    }*/
  }
}
