@txn
Feature: Работа с не производственными активностями сотруднуков склада

  @success
  Scenario: Старт нпо с предопределенной длительностью
    When пользователь "test_client_user" начинает нпо "LUNCH"
    Then у пользователя "test_client_user" есть действующее нпо "LUNCH"

  @success
  Scenario: Старт нпо с типом прочее с указанием длительности
    Given сейчас 2024-02-01 15:00:00
    When пользователю "test_client_user" назначают нпо "Prochie_raboty_drugoe" с текущего момента до 2024-02-01 20:00:00
    Then у пользователя "test_client_user" есть действующее нпо "Prochie_raboty_drugoe"

  @success
  Scenario: Старт нпо с типом прочее с указанием длительности когда сотрудник на обеде
    Given сейчас 2024-02-01 15:00:00
    When пользователь "test_client_user" начинает нпо "LUNCH"
    And сейчас 2024-02-01 15:05:00
    And пользователю "test_client_user" назначают нпо "Prochie_raboty_otgruzka" с текущего момента до 2024-02-01 20:00:00
    Then у пользователя "test_client_user" есть действующее нпо "LUNCH"

  @success
  Scenario: Старт нпо с типом прочее с указанием длительности затем начало обеда
    Given сейчас 2024-02-01 15:00:00
    When пользователю "test_client_user" назначают нпо "Prochie_raboty_drugoe" с текущего момента до 2024-02-01 20:00:00
    And сейчас 2024-02-01 15:15:00
    And пользователю "test_client_user" назначают нпо "Prochie_raboty_otgruzka" с текущего момента до 2024-02-01 20:00:00
    Then у пользователя "test_client_user" есть действующее нпо "Prochie_raboty_otgruzka"

  @success
  Scenario: Планирование активности в будущее
    Given сейчас 2024-02-01 15:00:00
    When пользователю "test_client_user" назначают нпо "Prochie_raboty_drugoe" с текущего момента до 2024-02-01 20:00:00
    And сейчас 2024-02-01 15:15:00
    And пользователю "test_client_user" назначают нпо "Prochie_raboty_otgruzka" с 2024-02-01 16:00:00 до 2024-02-01 20:00:00
    Then у пользователя "test_client_user" есть действующее нпо "Prochie_raboty_drugoe"

  @success
  Scenario: Планирование активности в будущее затем старт перед запланированным
    Given сейчас 2024-02-01 15:00:00
    When пользователю "test_client_user" назначают нпо "Prochie_raboty_otgruzka" с 2024-02-01 16:00:00 до 2024-02-01 20:00:00
    And сейчас 2024-02-01 15:15:00
    And пользователю "test_client_user" назначают нпо "Prochie_raboty_drugoe" с 2024-02-01 15:00:00 до 2024-02-01 20:00:00
    Then у пользователя "test_client_use" есть действующее нпо "Prochie_raboty_drugoe"

  @success
  Scenario: Планирование активности в будущее затем старт перед запланированным с ожиднием отложенного старта
    Given сейчас 2024-02-01 15:00:00
    When пользователю "test_client_user" назначают нпо "Prochie_raboty_otgruzka" с 2024-02-01 16:00:00 до 2024-02-01 20:00:00
    And сейчас 2024-02-01 15:15:00
    And пользователю "test_client_user" назначают нпо "Prochie_raboty_drugoe" с 2024-02-01 15:00:00 до 2024-02-01 20:00:00
    And сейчас 2024-02-01 16:05:00
    Then у пользователя "test_client_use" есть действующее нпо "Prochie_raboty_otgruzka"

  @success
  Scenario: Планирование активности в прошлое
    Given сейчас 2024-02-01 15:00:00
    When пользователю "test_client_user" назначают нпо "Prochie_raboty_drugoe" с текущего момента до 2024-02-01 20:00:00
    And сейчас 2024-02-01 15:15:00
    And пользователю "test_client_user" назначают нпо "Prochie_raboty_otgruzka" с 2024-02-01 14:50:00 до 2024-02-01 20:00:00
    Then у пользователя "test_client_use" есть действующее нпо "Prochie_raboty_otgruzka"

  @success
  Scenario: Изменение планового времени заввершения
    Given сейчас 2024-02-01 15:00:00
    When пользователю "test_client_user" назначают нпо "Prochie_raboty_drugoe" с текущего момента до 2024-02-01 20:00:00
    And сейчас 2024-02-01 15:15:00
    And пользователю "test_client_user" назначают нпо "Prochie_raboty_drugoe" с 2024-02-01 15:00:00 до 2024-02-01 16:00:00
    And сейчас 2024-02-01 16:00:00
    Then у пользователя "test_client_user" нет действующих нпо

