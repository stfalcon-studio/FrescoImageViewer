### This project is no longer supported. If you're able to switch from Fresco to any other library that works with the Android's ImageView, please migrate to the [StfalconImageViewer](https://github.com/stfalcon-studio/StfalconImageViewer) which is a better version of the FrescoImageViewer. Otherwise, you can still use the latest version of this library. Anyway, PRs are welcome!

# FrescoImageViewer

[![codebeat badge](https://codebeat.co/badges/60dbbc38-c85e-4680-b25b-6b04d859e904)](https://codebeat.co/projects/github-com-stfalcon-studio-frescoimageviewer-master) [ ![Download](https://api.bintray.com/packages/troy379/maven/FrescoImageViewer/images/download.svg) ](https://bintray.com/troy379/maven/FrescoImageViewer/_latestVersion)

Simple customizable full screen image viewer for [Fresco library][frescoRepo] that includes "pinch to zoom" and "swipe to dismiss" gestures.
Based on [PhotoDraweeView][photoDraweeViewRepo] by [ongakuer][coauthor].

![alt tag](images/fresco_image_viewer_demo.gif)

### Who we are
Need iOS and Android apps, MVP development or prototyping? Contact us via info@stfalcon.com. We develop software since 2009, and we're known experts in this field. Check out our [portfolio](https://stfalcon.com/en/portfolio) and see more libraries from [stfalcon-studio](https://stfalcon-studio.github.io/).

## Requirements

* Fresco v.0.12.0 and higher
* SDK 14 and and higher

## Demo Application

[![Get it on Google Play](https://play.google.com/intl/en_us/badges/images/badge_new.png)](https://play.google.com/store/apps/details?id=com.stfalcon.frescoimageviewersample)

## Usage

#### Simple usage
All you need to show a viewer is pass the context, start position and List<String> or String[] into builder and call `show()`.
```java
new ImageViewer.Builder(context, list)
        .setStartPosition(startPosition)
        .show();
```

#### Custom objects
But what if in your application images are represented not only with urls? For example, you have object with url and description? You'll have to convert it to list of Strings and only then pass it to viewer, right?
No, it's unnecessary! With `ImageViewer.Formatter` you can pass list of your custom images to viewer and simply write a rule for url extracting:
```java
List<CustomImage> images = getImages();
new ImageViewer.Builder<>(this, images)
        .setFormatter(new ImageViewer.Formatter<CustomImage>() {
            @Override
            public String format(CustomImage customImage) {
                return customImage.getUrl();
            }
        })
        .show();
```
If formatter isn't passed, `Object.toString()` will be used for image formatting as default behavior.

Piece of cake! :cake: :wink:

## Reminder
Don't forget to initialize Fresco in your Application class:
```java
Fresco.initialize(this);
```

And if you expect to open really large images, use configuration below for better performance:
```java
ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
    .setProgressiveJpegConfig(new SimpleProgressiveJpegConfig())
    .setResizeAndRotateEnabledForNetwork(true)
    .setDownsampleEnabled(true)
    .build();
Fresco.initialize(this, config);
```

## Customizing

You can also customize a viewer to fit your needs.

#### Background
Use `setBackgroundColorRes(colorRes)` or `setBackgroundColor(colorInt)` to set color for fading background.

#### Custom overlay view
If you need some content over the image (e.g. sharing or download button, description, numeration etc.) you can set your custom view using `setOverlayView(customView)` and bind it with viewer through `ImageViewer.OnImageChangeListener`.

#### Custom drawee hierarchy
Of course, according to Fresco flexibility, you can use your custom GenericDraweeHierarchy.
To do this you simply need to create GenericDraweeHierarchy**Builder** and pass it into builder:
```java
GenericDraweeHierarchyBuilder hierarchyBuilder = GenericDraweeHierarchyBuilder.newInstance(getResources())
        .setFailureImage(R.drawable.failureDrawable)
        .setProgressBarImage(R.drawable.progressBarDrawable)
        .setPlaceholderImage(R.drawable.placeholderDrawable);

builder.setCustomDraweeHierarchyBuilder(hierarchyBuilder)
```

:exclamation:**But there is a limitation**: default ScaleType in hierarchy is `ScaleType.FIT_CENTER`, so custom value will be ignored

#### Custom image requests
For rare cases like post-processing or bitmap resizing you need to use your custom ImageRequestBuilder.
Create it with `ImageViewer.createImageRequestBuilder()` and after configuration pass it to viewer through `setCustomImageRequestBuilder(ImageRequestBuilder)`.
```java
builder.setCustomImageRequestBuilder(
            ImageViewer.createImageRequestBuilder()
                    .setPostprocessor(new GrayscalePostprocessor()));
```

#### Image margin
Simply add margins between images with dimens with setImageMargin(context, dimen) or in `px` using `setImageMarginPx(marginPx)`.

#### Container padding
Overlay image hides part of image? Set container padding with dimens using `setContainerPadding(context, start, top, end, bottom)` or `setContainerPadding(context, dimean)` for all sides at once.
For setting padding in pixels, just use `setContainerPaddingPx(...)` method.

#### Status bar visibility
To show/hide status bar in view property you can set `hideStatusBar(boolean)` in builder. The default value is `true`.

#### Gestures disabling
If you need to disable some of gestures - do it using `allowSwipeToDismiss(boolean)` and `allowZooming(boolean)` accordingly.

Here is an example that sets all the possible options:

```java
new ImageViewer.Builder<>(this, images)
        .setStartPosition(startPosition)
        .hideStatusBar(false)
        .allowZooming(true)
        .allowSwipeToDismiss(true)
        .setBackgroundColorRes(colorRes)
        //.setBackgroundColor(color)
        .setImageMargin(margin)
        //.setImageMarginPx(marginPx)
        .setContainerPadding(this, dimen)
        //.setContainerPadding(this, dimenStart, dimenTop, dimenEnd, dimenBottom)
        //.setContainerPaddingPx(padding)
        //.setContainerPaddingPx(start, top, end, bottom)
        .setCustomImageRequestBuilder(imageRequestBuilder)
        .setCustomDraweeHierarchyBuilder(draweeHierarchyBuilder)
        .setImageChangeListener(imageChangeListener)
        .setOnDismissListener(onDismissListener)
        .setOverlayView(overlayView)
        .show();

```

With this possibilities you can achieve something like this:

![alt tag](images/fresco_image_viewer_customizing_demo.gif)

You can take a look at [sample project][sample] for more information.

## Install

Download via **Gradle**:
```gradle
compile 'com.github.stfalcon:frescoimageviewer:0.5.0'
```

or **Maven**:
```xml
<dependency>
  <groupId>com.github.stfalcon</groupId>
  <artifactId>frescoimageviewer</artifactId>
  <version>0.5.0</version>
  <type>pom</type>
</dependency>
```

## Changelog
[See the changelog](docs/CHANGELOG.md) to be aware of latest improvements and fixes.

## License

```
Copyright (C) 2017 stfalcon.com

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


