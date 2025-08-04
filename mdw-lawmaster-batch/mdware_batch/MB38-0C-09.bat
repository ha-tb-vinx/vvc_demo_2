@echo off
rem =======================================
rem MB38-0C-09 新店用マスタ作成処理
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MB38-0C-09 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
