- 为解决项目开发中大量的 shape 资源文件定义工作, 只要需求的颜色、圆角角度或者其他参数有任意变化我都需要去新建一个 xml 资源文件，最后资源文件特别多写起来还有一定程度的费时力。故自定义控件来解决此问题

### 效果

![image](./image/SampleImage.png)

### 使用

```

<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#FFF">

    <com.custom.view.julivetextview.RectTextView
        android:id="@+id/rt1"
        android:layout_width="30dp"
        android:layout_height="16dp"
        android:layout_margin="20dp"
        android:text="在售"
        android:textColor="#12C48E"
        android:textSize="11sp"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:rectColor="#E1FBF3"
        app:rectRadius="2dp" />

    <com.custom.view.julivetextview.RectTextView
        android:id="@+id/rt2"
        android:layout_width="30dp"
        android:layout_height="16dp"
        android:layout_margin="20dp"
        android:text="待售"
        android:textColor="#00C0EB"
        android:textSize="11sp"
        app:layout_constraintLeft_toRightOf="@id/rt1"
        app:layout_constraintTop_toTopOf="parent"
        app:rectColor="#EFFBFE"
        app:rectRadius="2dp" />

    <com.custom.view.julivetextview.RectTextView
        android:id="@+id/rt3"
        android:layout_width="30dp"
        android:layout_height="16dp"
        android:layout_margin="20dp"
        android:text="售罄"
        android:textColor="#7E8C8F"
        android:textSize="11sp"
        app:layout_constraintLeft_toRightOf="@id/rt2"
        app:layout_constraintTop_toTopOf="parent"
        app:rectColor="#F0F4F5"
        app:rectRadius="2dp" />


    <com.custom.view.julivetextview.RectTextView
        android:id="@+id/rt4"
        android:layout_width="52dp"
        android:layout_height="16dp"
        android:layout_margin="20dp"
        android:text="标签样式"
        android:textColor="#FFF"
        android:textSize="11sp"
        app:layout_constraintLeft_toRightOf="@id/rt3"
        app:layout_constraintTop_toTopOf="parent"
        app:rectColor="#0DCDF8"
        app:rectRadius="2dp" />


    <com.custom.view.julivetextview.RectTextView
        android:id="@+id/rt5"
        android:layout_width="118dp"
        android:layout_height="42dp"
        android:layout_margin="20dp"
        android:text="我是文案"
        android:textColor="#FFF"
        android:textSize="16sp"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintTop_toBottomOf="@id/rt1"
        app:rectColor="#FA5F35"
        app:rectRadius="4dp" />

    <com.custom.view.julivetextview.CircularTextView
        android:id="@+id/ct6"
        android:layout_width="230dp"
        android:layout_height="44dp"
        android:layout_margin="20dp"
        android:text="我是文案"
        android:textColor="#00C0EB"
        android:textSize="16sp"
        app:circularRadius="22dp"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintTop_toBottomOf="@id/rt5" />

    <com.custom.view.julivetextview.CircularTextView
        android:layout_width="68dp"
        android:layout_height="25dp"
        android:layout_margin="20dp"
        android:text="我是文案"
        android:textColor="#FA5F35"
        android:textSize="12sp"
        app:circularRadius="12.5dp"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintTop_toBottomOf="@id/ct6" />


</android.support.constraint.ConstraintLayout>


```


RectTextView 为实心圆角矩形, 需要指定 rectColor 矩形背景颜色 以及 圆角角度自定义属性
CircularTextView 空心边框线, 需要指定 circularRadius 圆角角度自定义属性

RectTextView 和 CircularTextView 的点击事件和 TextView 一致

## 了解更多

https://blog.csdn.net/qq_19986309/article/details/96989955  


