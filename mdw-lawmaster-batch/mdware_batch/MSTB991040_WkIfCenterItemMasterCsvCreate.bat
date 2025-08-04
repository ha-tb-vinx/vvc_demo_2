@echo off
rem =======================================
rem MSTB991040_WkIfCenterItemMasterCsvCreate.bat
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB991040 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
