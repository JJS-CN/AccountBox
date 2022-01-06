import 'package:get/get.dart';
import 'package:dio/dio.dart';

class DioManager {
   Dio _dio = Dio();

  DioManager() {
    _dio.options.baseUrl = "http://120.77.85.84";
  }

    Future<String?> postJson(
      String path, Map<String, dynamic> map) async {
    var response = await _dio.post(path, data: map);
    if (response.statusCode == 200) {
      return response.data;
    } else {
      return response.statusMessage;
    }
    return null;
  }

   get(
      String path, Map<String, dynamic> map) async {
    var response = await _dio.get(path, queryParameters: map);
    if (response.statusCode == 200) {
      return response.data;
    } else {

    }
    return null;
  }
}
