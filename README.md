[ ![Download](https://api.bintray.com/packages/troy379/maven/FrescoImageViewer/images/download.svg) ](https://bintray.com/troy379/maven/FrescoImageViewer/_latestVersion)

# FrescoImageViewer

Simple customizable full screen image viewer for [Fresco library] [frescoRepo] that includes "pinch to zoom" and "swipe to dismiss" gestures.
Based on [PhotoDraweeView] [photoDraweeViewRepo] by [ongakuer] [coauthor].

![alt tag](images/fresco_image_viewer_demo.gif)

### Requirements

* Fresco v.0.12.0 and higher
* SDK 14 and and higher

### Download

Download via Gradle:
```gradle
compile 'com.github.stfalcon:frescoimageviewer:0.4.1'
```

or Maven:
```xml
<dependency>
  <groupId>com.github.stfalcon</groupId>
  <artifactId>frescoimageviewer</artifactId>
  <version>0.4.1</version>
  <type>pom</type>
</dependency>
```

### Usage

All you need to show a viewer is pass the context, start position and List<String> or String[] into builder and call `show()`.

```java
new ImageViewer.Builder(context, list)
                .setStartPosition(startPosition)
                .show();
```
Piece of cake! :cake: :wink:

### Customizing

You can also customize a viewer to fit your needs.

###### Background
Use `setBackgroundColorRes(colorRes)` or `setBackgroundColor(colorInt)` to set color for fading background.

###### Custom overlay view
If you need some content over the image (e.g. sharing or download button, description, numeration etc.) you can set your custom view using `setOverlayView(customView)` and bind it with viewer through `ImageViewer.OnImageChangeListener`.

###### Custom drawee hierarchy
Of course, according to Fresco flexibility, you can use your custom GenericDraweeHierarchy.
To do this you simply need to create GenericDraweeHierarchy**Builder** and pass it into builder:
```java
GenericDraweeHierarchyBuilder hierarchyBuilder = GenericDraweeHierarchyBuilder.newInstance(getResources())
                .setFailureImage(R.drawable.failureDrawable)
                .setProgressBarImage(R.drawable.progressBarDrawable)
                .setPlaceholderImage(R.drawable.placeholderDrawable);

        new ImageViewer.Builder(context, urls)
                ...
                .setCustomDraweeHierarchyBuilder(hierarchyBuilder)
                .show();
```

**But there is a limitation**: default ScaleType in hierarchy is `ScaleType.FIT_CENTER`, so custom value will be ignored

###### Image margin
Simply add margins between images with dimens with setImageMargin(context, dimen) or in `px` using `setImageMarginPx(marginPx)`.

###### Status bar visibility
To show/hide status bar in view property you can set `hideStatusBar(boolean)` in builder. The default value is `strue`.

Here is an example that sets possible options:

```java
new ImageViewer.Builder(context, list)
                .setStartPosition(startPosition)
                //.hideStatusBar(false)
                .setBackgroundColorRes(colorRes)
                //.setBackgroundColor(color)
                .setImageMargin(R.dimen.image_margin)
                .setImageChangeListener(changeListener)
                .setOnDismissListener(dismissListener)
                .setCustomDraweeHierarchyBuilder(hierarchyBuilder)
                .setOverlayView(overlayView)
                .show();

```

With this possibilities you can achieve something like this:

![alt tag](images/fresco_image_viewer_customizing_demo.gif)

You can take a look at [sample project] [sample] for more information.

### License

```
Copyright (C) 2016 stfalcon.com

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
