@echo off
rem =======================================
rem MSTB907-010 商品マスタ変更リスト（バッチ）作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB907-010 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
