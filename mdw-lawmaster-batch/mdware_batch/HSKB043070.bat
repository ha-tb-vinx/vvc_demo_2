@echo off
rem =======================================
rem HSKB043070 DWH用特売マスタファイルバックアップ
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController HSKB043070 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
