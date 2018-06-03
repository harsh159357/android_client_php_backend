android_client_php_backend
=============

**App Demonstrating the use of Android and PHP.**

>- App Logo Created Using [Logo Maker](http://logomakr.com)
>- For Hosting used [Hostinger](https://www.hostinger.in/)
>- For Api Interfacing with PHP on App used [Retrofit](https://github.com/square/retrofit)


**How to use this Sample App.**
>- Host the sample included inside [phpbackend](https://github.com/harsh159357/android_client_php_backend/tree/master/phpbackend) on preffered web hosting  
>- Create a table inside your database using [AndroidClientPhpBackend.sql](https://github.com/harsh159357/android_client_php_backend/blob/master/phpbackend/AndroidClientPhpBackend.sql)
>- If you are using your own website just edit this thing inside [Util.java](https://github.com/harsh159357/android_client_php_backend/blob/master/app/src/main/java/com/harsh/androidclientphpbackend/util/Util.java)

    public static final String BASE_URL = "http://hafinse.pe.hu/";

>- Make sure to edit [DBOperations.php](https://github.com/harsh159357/android_client_php_backend/blob/master/phpbackend/DBOperations.php) and change following things -

    private $host = 'your_host'; 
    private $user = 'your_user_name';
    private $db = 'your_database';
    private $pass = 'your_password';
>- Not Interested in doing above steps just clone this repo and use it as it is already hosted on Free Web Host Hostinger [hafinse](https://hafinse.pe.hu)


**Things Implemented**
>- Splash
>- Login
>- Registration
>- Change Password


**Screenshots**

<div id="images" style="#images {
    white-space: nowrap;
}">
<img src="screenshots/Splash.png" alt="Splash" width="150" height="250">
<img src="screenshots/Home.png" alt="Home" width="150" height="250">
<img src="screenshots/Login.png" alt="Login" width="150" height="250">
<img src="screenshots/Registration.png" alt="Registration" width="150" height="250">
<img src="screenshots/Change_Password.png" alt="Change Password" width="150" height="250">
</div>
<br/>

**Its Flutter CounterPart [Click Here](https://github.com/harsh159357/flutter_client_php_backend)**

**If you are aware of [Postman](https://www.getpostman.com/) you can use [Postman Collection](https://www.getpostman.com/collections/80394d2fc7c2aed05cc5) for Rest API used in this repo**

License
-------

    Copyright 2017 Harsh Sharma

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
