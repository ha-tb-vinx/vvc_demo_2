@echo off
rem =======================================
rem MSTB994-071 指定日POS用ファイル作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB994-071 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
