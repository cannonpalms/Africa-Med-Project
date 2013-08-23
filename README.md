Africa-Med-Project
==================

A collaboration by the students of Southside High School, in correspondence with Dr. Delphine Dean of Clemson University. 


==================


Using GitHub with Eclipse
==================

  1. Open Eclipse. Go to the Eclipse Marketplace, found under the Help menu (File, Edit, etc.)
  2. Search for and install `egit`. Follow any prompts given.
  3. Clone this git project into the Eclipse workspace. **Important: Make sure that the project is cloned to the default directory that New Projects are created in.**
  4. Create a new Java project. In the name field, type `Africa-Med-Project`. Eclipse will detect that there is already a project there (the one you just cloned) and import it. Click Finish.

You're now good to go! If you need help with the little things such as making changes, pushing your changes to a branch, or other git functions, see the tutorials on the GitHub website.

To start off, go ahead and create a new branch on the project in your name, i.e. Cannon will create a branch called `cannon`. This enables each member of the team to keep his own work updated, while allowing all other team members to access what another member is working on. When your feature is tested and complete, merge it with the `dev` branch. The `dev` branch will be used to ensure that each team member's features fit together and function together. Finally, when fully functional, the `dev` branch will be pushed to the `master` branch!


Using JUnit
==================

Workflow:

  1. Write/modify code.
  2. Create one JUnit Test Case per class you wrote/modified. Follow namespace patterns. If you create a class `Person`, you should name the JUnit Test Case `PersonTests`.
  3. Write one JUnit test (in a method annotated by the `@Test` annotation before the method) per method in your code.
  4. Fix your bugs (assume your tests may be bugged as well), and when all tests pass, *merge your branch with the `dev`*.
  5. ???
  6. Profit.
