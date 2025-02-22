RegistrationSDK
=======
The Registration SDK is a modular Android library designed for seamless user registration and authentication.
Developed for Valify, this SDK provides a structured and efficient way to handle user registration by collecting user details and 
verifying identity through facial recognition.

## Preview
<div align=center>
<img src="https://raw.githubusercontent.com/eslamandroid/RegistrationSDK/main/resources/demo.gif" width="300px" alt="preview"/>
</div>


Download
--------

```groovy
implementation("com.github.eslamandroid:RegistrationSDK:1.0.0")
```
or Maven:
```xml

<dependency>
    <groupId>com.github.eslamandroid</groupId>
    <artifactId>RegistrationSDK</artifactId>
    <version>1.0.0</version>
</dependency>
```

Usage
--------
```kotlin
RegistrationSDK.startRegistration(context)
```

License
--------

    Copyright 2013 Square, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       https://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.