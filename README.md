A Jokes App which allows user to search for jokes with custom filters and save locally to device using room database
Jokes App is implemented with MVVM and clean architecture which has data layer, domain layer, presentation layer. 
Domain layer has "USE CASES" for invoking data layer with repository.

App uses HILT library for dependency injection although test cases are not written but app architecture is designed 
in such a way that its easy to mock data and domain layer using HILT
