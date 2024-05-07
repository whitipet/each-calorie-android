-repackageclasses # Get rid of package names, makes file smaller

#region FIXME: Get rid of, temporary rules.
-ignorewarnings # https://developer.android.com/build/releases/past-releases/agp-7-0-0-release-notes#r8-missing-class-warning

-keep class data.DataDIKt { *; }
-keep class feature.consumption.ConsumptionDIKt { *; }
-keep class feature.consumption.ConsumptionNavigationKt { *; }
-keep class feature.home.HomeDIKt { *; }
-keep class feature.home.HomeNavigationKt { *; }
-keep class feature.set_goal.SetGoalDIKt { *; }
-keep class feature.set_goal.SetGoalNavigationKt { *; }
-keep class project.ui.theme.ThemeKt { *; }
-keep class data.database.DatabaseDIKt { *; }
-keep class data.database.data_source.ConsumptionDatabaseDataSource { *; }
-keep class data.database.data_source.GoalDatabaseDataSource { *; }
-keep class data.Repository { *; }
-keep class data.database.entity.Consumption { *; }
-keep class data.database.entity.Goal { *; }

-keepnames class feature.** { *; }
-keepnames class data.** { *; }
#endregion