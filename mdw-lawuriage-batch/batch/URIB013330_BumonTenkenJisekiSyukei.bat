REM @echo off
REM ============================================================================
REM タイトル	：実績集計処理（部門点検）
REM 説明	：時間帯データ（FORMAT2）および部門点検集計ワークからTMP部門点検データを作成する。
REM version 1.00 2016/05/20 Hirata:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== 実績集計処理（部門点検）
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB013330%1
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1
