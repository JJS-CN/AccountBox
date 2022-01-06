import 'package:accountbox/bean/base_response.dart';
import 'package:accountbox/model/user_provider.dart';
import 'package:get/get_connect/http/src/response/response.dart';

abstract class IUserRepository {
  Future<BaseResponse> register(String passport, String password);
}

class UserRepository implements IUserRepository {
  UserRepository({required this.userProvider});

  //由外部初始化
  final UserProvider userProvider;

  @override
  Future<BaseResponse> register(String passport, String password) {
    return userProvider.register(passport, password);
  }
}
