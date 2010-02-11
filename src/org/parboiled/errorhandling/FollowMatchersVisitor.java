/*
 * Copyright (C) 2009-2010 Mathias Doenitz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.parboiled.errorhandling;

import org.jetbrains.annotations.NotNull;
import org.parboiled.MatcherContext;
import org.parboiled.matchers.*;

import java.util.List;

/**
 * Collects the matchers that can legally follow the currently running matcher according to the grammar into a given
 * list. The visitor returns true if the collected matchers are all possible followers, and false if other matchers
 * higher up the rule stack can also follow.
 *
 * @param <V>
 */
public class FollowMatchersVisitor<V> extends DefaultMatcherVisitor<V, Boolean> {

    private final CanMatchEmptyVisitor<V> canMatchEmptyVisitor = new CanMatchEmptyVisitor<V>();
    private final List<Matcher<V>> list;
    private MatcherContext<V> context;

    public FollowMatchersVisitor(List<Matcher<V>> list) {
        this.list = list;
    }

    public void setContext(@NotNull MatcherContext<V> context) {
        this.context = context;
    }

    @Override
    public Boolean visit(OneOrMoreMatcher<V> matcher) {
        // since this call is only legal when we are currently within a match of the sub matcher,
        // i.e. the submatcher can either match once more or the repetition can legally terminate which means
        // our follower set addition is incomplete -> return false
        list.add(matcher.subMatcher);
        return false;
    }

    @Override
    public Boolean visit(SequenceMatcher<V> matcher) {
        for (int i = context.getIntTag() + 1; i < matcher.getChildren().size(); i++) {
            Matcher<V> child = matcher.getChildren().get(i);
            list.add(child);
            if (!child.accept(canMatchEmptyVisitor)) return true;
        }
        return false;
    }

    @Override
    public Boolean visit(ZeroOrMoreMatcher<V> matcher) {
        list.add(matcher.subMatcher);
        return false;
    }

    @Override
    public Boolean defaultValue(AbstractMatcher<V> matcher) {
        return false;
    }

}
