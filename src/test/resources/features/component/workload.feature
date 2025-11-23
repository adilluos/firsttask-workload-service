Feature: Workload component tests

  Scenario: Updating workload with ADD event
    When I send ADD workload update for trainer "test.trainer"
    Then trainer "test.trainer" should have workload 50 for year 2025 month 11

#  Scenario: Invalid request should return error
#    When I send workload update with missing fields
#    Then the response status should be 400
