Feature: User management
  Scenario: Retrieve adminstrator user
      When I search user 'admin'
      Then the user is found
      And his last name is 'Administrator'