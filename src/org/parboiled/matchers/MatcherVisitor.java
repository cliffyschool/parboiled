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

package org.parboiled.matchers;

public interface MatcherVisitor<V, R> {

    R visit(ActionMatcher<V> matcher);

    R visit(AnyCharMatcher<V> matcher);

    R visit(CharIgnoreCaseMatcher<V> matcher);

    R visit(CharMatcher<V> matcher);

    R visit(CharRangeMatcher<V> matcher);

    R visit(EmptyMatcher<V> matcher);

    R visit(FirstOfMatcher<V> matcher);

    R visit(OneOrMoreMatcher<V> matcher);

    R visit(OptionalMatcher<V> matcher);

    R visit(SequenceMatcher<V> matcher);

    R visit(TestMatcher<V> matcher);

    R visit(TestNotMatcher<V> matcher);

    R visit(ZeroOrMoreMatcher<V> matcher);

}
