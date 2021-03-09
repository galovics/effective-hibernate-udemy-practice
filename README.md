# Effective Hibernate Udemy practices
The repo contains the practice exercises for the Effective Hibernate course on Udemy. 

Link to the course: TBD

## entity-transitions-practice
Contains all the relevant practice cases for entity state transitions. It focuses around fixing the merging problem within an application.

## lazy-loading-practice
This practice is oriented around lazy-loading issues and fixing them.

## projections-practice
The projections practice is tackling the concept of making database reads as performant as possible.

## concurrency-control-practice
The project captures the usage of optimistic locking.

## batching-practice
The batching practice ensures you understand the key ideas around statement batching.

# Running the practices
All the practices are using Java 11 as a source version but the code is actually not relying on any Java 11 features.

Any of the practices can be run by executing the following command:
```shell
$ ./gradlew clean build
```

# Fixing the practices
The practices are using different tools to ensure the correct behavior, amount of queries and the performance of some of the statements. 

The code is structured in a way that none of the tests need to be changed, but only the underlying implementation.

Happy coding.

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