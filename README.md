# FrescoImageViewer

Simple full screen image viewer for [Fresco library] [frescoRepo] that includes "pinch to zoom" and "swipe to dismiss" gestures.
Based on [PhotoDraweeView] [photoDraweeViewRepo] by [ongakuer] [coauthor].

![alt tag](images/fresco_image_viewer_demo.gif)

### Requirements

* Fresco v.0.12.0 and higher
* SDK 14 and and higher

### Download

Download via Gradle:
```gradle
compile 'com.github.stfalcon:frescoimageviewer:0.1.0'
```

or Maven:
```xml
<dependency>
  <groupId>com.github.stfalcon</groupId>
  <artifactId>frescoimageviewer</artifactId>
  <version>0.1.0</version>
  <type>pom</type>
</dependency>
```

### Usage

All you need to show viewer is pass the context and ArrayList<String> or String[] into builder and show it.
You can also set color for fading background and position of default item.

```java
new ImageViewer.Builder(context, list)
                .setStartPosition(startPosition)
                .setBackgroundColorRes(R.color.colorPrimaryDark)
                .show();
```
Piece of cake! :)
You can take a look at [sample project] [sample] for more information.

### License

```
Copyright (C) 2016 Alexander Krol, stfalcon.com

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

```

[sample]: <https://github.com/stfalcon-studio/FrescoImageViewer/tree/master/sample>
[frescoRepo]: <https://github.com/facebook/fresco>
[photoDraweeViewRepo]: <https://github.com/ongakuer/PhotoDraweeView>
[coauthor]: <https://github.com/ongakuer>
