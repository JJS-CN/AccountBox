import 'package:accountbox/bean/base_response.dart';
import 'package:get/get.dart';

class BaseProvider extends GetConnect {
  @override
  void onInit() {
    // TODO: implement onInit
    super.onInit();
    httpClient.baseUrl = "http://120.77.85.84";
    //请求拦截
    httpClient.addRequestModifier<void>((request) {
      request.headers['Authorization'] = '123456';
      return request;
    });
    //响应拦截
    httpClient.addResponseModifier((request, response) {
      return response;
    });
  }
}
