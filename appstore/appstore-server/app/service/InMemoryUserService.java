/**
 * Copyright 2012 Jorge Aliss (jaliss at gmail dot com) - twitter: @jaliss
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package service;

import models.User;
import play.Application;
import securesocial.core.java.BaseUserService;
import securesocial.core.java.SocialUser;
import securesocial.core.java.Token;
import securesocial.core.java.UserId;

import java.util.HashMap;

/**
 * A Sample In Memory user service in Java
 */
public class InMemoryUserService extends BaseUserService {
    private HashMap<String, SocialUser> users  = new HashMap<String,SocialUser>();

    public InMemoryUserService(Application application) {
        super(application);
    }


    public SocialUser doFind(UserId userId) {
        return User.getSocialUser(userId.id );
    }

    @Override
    public Token doFindToken(String s) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public SocialUser doFindByEmail(String s, String s1) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void doDeleteToken(String s) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void doDeleteExpiredTokens() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void doSave(SocialUser user) {
        User.create(user);
    }

    @Override
    public void doSave(Token token) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
