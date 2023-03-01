package org.micro.los.common.manager;


import org.micro.commons.basic.beans.Result;

public interface Action {

    void doFailed(Result result);

    void doSuccess(Result result);

}
