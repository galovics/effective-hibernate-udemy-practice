# Entity state transitions practice
This practice application serves as an example usage for JPA's 
entity state transition.
 
The application was created to help the people taking my course on
Udemy, but if you just want to get some practical knowledge on
entity state transitions, it's a good way to try out 
the practice.

The Udemy course is available here: 

## The practice
The application comes with a full suite of tests that just need to be 
executed. It automatically verifies if the code relies on the proper
state transitions.

The test code doesn't need to be modified at all, only the underlying
implementation and the configuration.

The practice focuses on the following topics:
- Entity state transitions
- Modifying entities properly

The test code comes with inline comments and javadoc, so it's easier
to understand what the actual test case is verifying.

## Building & verification
Verifying your changes is pretty easy. Just execute the tests under
`src/test/java`. You can do this either via an IDE like IntelliJ or
Eclipse.

Or you can open a terminal and execute the following command:
```bash
$ ./gradlew clean build
```
This will build the application from scratch and execute the available
test cases. 
If you see the end result as `BUILD SUCCESSFUL` then you successfully
implemented, and understood entity state transitions. 
Congratulations.

## Feedback and questions
I always appreciate feedback. If you think the practice was useful,
let me know. If you think it needs some polishing, that's also welcomed.

Also, don't hesitate to contact me in case you have questions or ideas,
I'm happy to help. 

You can reach me at Twitter [@ArnoldGalovics](https://twitter.com/ArnoldGalovics)
or [info@arnoldgalovics.com](mailto:info@arnoldgalovics.com).

# License
```text
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```