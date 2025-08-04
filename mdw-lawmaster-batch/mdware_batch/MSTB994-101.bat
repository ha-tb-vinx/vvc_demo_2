@echo off
rem =======================================
rem MSTB994-101 指定日POS用テーブル バックアップ
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB994-101 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
