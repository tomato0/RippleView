# RippleView
#### 自定义水波紋View
#### 实现原理sin函数
---
使用Demo：  
``` java
        mRippleViewOne = (RippleView) findViewById(R.id.rippleview_one);
        mRippleViewTwo = (RippleView) findViewById(R.id.rippleview_two);

        mRippleViewOne.setRippleColor(0x11FFFFFF);
        mRippleViewOne.setRippleOffset(0f);
        mRippleViewOne.startFlowAnimator();

        mRippleViewTwo.setRippleColor(0x33FFFFFF);
        mRippleViewTwo.setRippleOffset(0.4f);
        mRippleViewTwo.setFlowSeconds(2);
        mRippleViewTwo.startFlowAnimator();
```
finish
