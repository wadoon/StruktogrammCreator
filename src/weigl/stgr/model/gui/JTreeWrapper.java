package weigl.stgr.model.gui;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface JTreeWrapper {
    Class<? extends NodeWrapper> value() default StgrCommandWrapper.class;
}
