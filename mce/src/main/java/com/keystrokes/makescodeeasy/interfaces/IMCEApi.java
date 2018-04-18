package com.keystrokes.makescodeeasy.interfaces;

import java.util.List;

/**
 * Created by mmathiarasan on 17-04-2018.
 */

public interface IMCEApi {

    public List<Class> loadApiClasses();

    public <T> T getApi(Class<T> clazz);
}
