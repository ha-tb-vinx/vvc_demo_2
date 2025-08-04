@echo off
rem =======================================
rem MSTB904-140 DWH用IFカテゴリマスタ作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB904-140 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
