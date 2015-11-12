BMIView
============
<img src="images/example.jpg" alt="Example" width="250px" height="491px">

_A simple view for android that calculates and shows your bmi value_

Usage
--------
```xml
<com.luigima.bmiview.BMIView
        android:id="@+id/bmiView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:verySeverelyUnderweightColor="@color/.."
        app:severelyUnderweightColor="@color/.."
        app:underweightColor="@color/.."
        app:normalColor="@color/.."
        app:overweightColor="@color/.."
        app:obeseClass1Color="@color/.."
        app:obeseClass2Color="@color/.."
        app:obeseClass3Color="@color/.." />
```

```java
bmiView.setGender(0)  // Male
       .setWeight(85f)  //kg
       .setHeight(1.81f); //m
```
Download
--------
```groovy
not yet
```


License
-------

    Copyright 2015 Lukas Malcher

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
