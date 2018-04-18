package com.keystrokes.makescodeeasy.com.bykerr.interfaces;

import java.util.List;
import java.util.Map;

/**
 * Created by mmathiarasan on 17-04-2018.
 */

public interface IMCEApi {

    public List<Class> loadApiClasses();

    public <T> T getApi(Class<T> clazz);
}
