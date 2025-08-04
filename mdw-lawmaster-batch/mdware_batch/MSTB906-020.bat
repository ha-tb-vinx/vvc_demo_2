@echo off
REM ============================================================================
REM タイトル：MSTB906 IF計上伝票加工取込
REM 説明　　：仕入管理より計上伝票情報を取得・加工を行う。POS売価チェックリスト出力時に使用。
REM version 3.00 2013/12/18 O.Uemura:新規
REM ============================================================================

call %~dp0\SetBatchPath.bat

REM ===== IF計上伝票加工取込処理
%execjava% mdware.common.batch.util.control.BatchController MSTB906-020 %CLS_HOME%\batch\properties release use
IF %ERRORLEVEL% NEQ 0 goto error

:end

exit 0

:error

exit 1
