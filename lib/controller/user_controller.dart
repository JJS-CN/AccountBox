import 'package:accountbox/bean/base_response.dart';
import 'package:accountbox/manager/dio_manager.dart';
import 'package:accountbox/model/user_repository.dart';
import 'package:dio/dio.dart';
import 'package:get/get.dart';
import 'package:accountbox/const/app_const.dart';

class UserController extends SuperController<BaseResponse> {
  UserController({required this.userRepository});

  final UserRepository userRepository;

  registerUser(String passport, String password) {
    userRepository.register(passport, password);
  }

  @override
  void onDetached() {
    // TODO: implement onDetached
  }

  @override
  void onInactive() {
    // TODO: implement onInactive
  }

  @override
  void onPaused() {
    // TODO: implement onPaused
  }

  @override
  void onResumed() {
    // TODO: implement onResumed
  }

}
