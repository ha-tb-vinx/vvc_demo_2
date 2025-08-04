@echo off
rem =======================================
rem MSTB904-190 DWH用テーブルバックアップ
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB904-190 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
