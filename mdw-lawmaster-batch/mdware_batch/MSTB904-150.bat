@echo off
rem =======================================
rem MSTB904-150 DWH用カテゴリマスタバックアップ
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB904-150 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
