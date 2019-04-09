import 'package:dio/dio.dart';
class NetWork{
static  void  post(String url,Map body,Callback callback ){
  Dio dio = new Dio();
  Response response;
  print("post" +url);
 dio.post(url,data: body);
  print("post after " + url);
  if(callback!=null && response.statusCode == 200) {
     callback.onSuccess(response.data);
  } else if(callback!=null) {
    callback.onFailure(response.statusCode, response.data);
    }
    }
    }

class Callback {
  void  onSuccess(var response){
    print(response.toString());
  }
  void onFailure(var errorCode,var errorMessage){
    print(errorCode + errorMessage);
  }
}
