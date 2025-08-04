@echo off
rem =======================================
rem MSTB101-050 グループ売変除外品バックアップ処理
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB101-050 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
