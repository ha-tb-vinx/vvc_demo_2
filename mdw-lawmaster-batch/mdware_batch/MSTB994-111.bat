@echo off
rem =======================================
rem MSTB994-111 指定日POS用IF種別マスタ作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB994-111 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
