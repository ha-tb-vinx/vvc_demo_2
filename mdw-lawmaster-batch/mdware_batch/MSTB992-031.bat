@echo off
rem =======================================
rem MSTB992-031 POS用カテゴリマスタバックアップ
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB992-031 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
