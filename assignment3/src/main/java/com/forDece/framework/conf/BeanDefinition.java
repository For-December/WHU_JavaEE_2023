package com.forDece.framework.conf;

public class BeanDefinition {
        public String id;
        public String clazz;

        public MiniProperty property;

    public static class MiniProperty{
        String name;
        String ref;
    }

}
