@echo off
rem =======================================
rem MSTB992-030 POS用カテゴリマスタバックアップ
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB992-030 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
